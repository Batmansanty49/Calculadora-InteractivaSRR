import xml.etree.ElementTree as ET
import re

def process_file(filename):
    with open(filename, 'r', encoding='utf-8') as f:
        content = f.read()

    # We will use regex to find <path ... /> and replace it if pathData is too large
    # It's safer to use regex here because standard xml parsers might mess up namespace prefixes or formatting
    
    def path_replacer(match):
        path_tag = match.group(0)
        
        path_data_match = re.search(r'android:pathData="([^"]+)"', path_tag)
        if not path_data_match:
            return path_tag
            
        path_data = path_data_match.group(1)
        if len(path_data) < 30000:
            return path_tag
            
        print(f"Splitting path in {filename} of length {len(path_data)}")
        
        # Split path_data by ZM
        # path_data looks like ...Z...ZM...Z...
        # We replace ZM with Z|M and then split by |
        parts = path_data.replace('ZM', 'Z|M').split('|')
        
        new_tags = []
        current_chunk = ""
        
        for part in parts:
            if len(current_chunk) + len(part) > 30000 and current_chunk:
                # Add current chunk as a new path
                new_tag = path_tag.replace(path_data, current_chunk)
                new_tags.append(new_tag)
                current_chunk = part
            else:
                if current_chunk:
                    current_chunk += part
                else:
                    current_chunk = part
                    
        if current_chunk:
            new_tag = path_tag.replace(path_data, current_chunk)
            new_tags.append(new_tag)
            
        return "\n".join(new_tags)

    # Find all <path ... /> or <path ...></path> tags
    # Wait, the tags in the file end with />
    # Let's match <path[^>]+/>
    new_content = re.sub(r'<path[^>]+/>', path_replacer, content)
    
    with open(filename, 'w', encoding='utf-8') as f:
        f.write(new_content)

process_file('app/src/main/res/drawable/trapecioimagen.xml')
process_file('app/src/main/res/drawable/prismaimagen.xml')
print("Done")
