import os

def connect_to_service():
    # VULNERABILITY: Hardcoded Credentials
    # Critical secrets should never be stored in source code.
    api_key = "AIzaSyD-12345-SECRET-KEY-EXAMPLE" 
    service_url = "https://api.internal.service/v1"
    
    print(f"Connecting to {service_url} with key {api_key}")
    return True

# Auto-generated update: 1770883678