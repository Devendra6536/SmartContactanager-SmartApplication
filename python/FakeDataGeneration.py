from faker import Faker
import sys
import csv
import json
import lxml
import openpyxl
import pandas as pd

# The first element of sys.argv is the script name itself
script_name = sys.argv[0]

# Check if at least three arguments are provided
if len(sys.argv) < 5:
    print(f"Usage: {script_name} <output_format> <row format> <data_type1> <data_type2> ...")
    sys.exit(1)

filename = sys.argv[1].lower()  # Extract the output format
output_format = sys.argv[2].lower()

num_rows = int(sys.argv[3])
output_file_Directory=sys.argv[4]
  # Extract the number of rows from command line arguments
#print(script_name,output_format,num_rows,sys.argv[3],sys.argv[4],sys.argv[5])
# Supported output formats and their corresponding delimiters
supported_formats = {
    "csv": ",",
    "json": None,  # JSON format doesn't use delimiters
    "tab": "\t",   # Tab-delimited format
    "custom":"",
    "xml": "",     # Placeholder for XML format
    "xlsx": "",
    "txt": " ",    # Space as a placeholder for plain text



    # Add more formats as needed
}

# Check if the specified output format is supported
if output_format not in supported_formats:
    print(f"Unsupported output format: {output_format}")
    sys.exit(1)

delimiter = supported_formats[output_format]

# Initialize Faker
fake = Faker()

# Create a dictionary to map data types to Faker methods
data_type_functions = {}
for data_type in sys.argv[5:]:
    data_type_functions[data_type] = getattr(fake, data_type)

# Create an empty dictionary to store data
data = {}

# Generate rows for each data type
for data_type, function in data_type_functions.items():
    data[data_type] = [function() for _ in range(num_rows)]

# Create a DataFrame from the dictionary and transpose it
df = pd.DataFrame(data)

# Output the DataFrame based on the specified format
if output_format == "csv":
   # df.to_csv("./{filename}.csv", index=False, sep=delimiter)
    df.to_csv(f"{output_file_Directory}/{filename}.csv", index=False, sep=delimiter)
elif output_format == "json":
    df.to_json(f"{output_file_Directory}/{filename}.json", orient="records")
elif output_format == "tab":
    df.to_csv(f"{output_file_Directory}/{filename}.tsv", index=False, sep=delimiter)
elif output_format == "xml":
    df.to_xml(f"{output_file_Directory}/{filename}.xml", index=False)
elif output_format == "xlxs":
    df.to_excel(f"{output_file_Directory}/{filename}.xlsx", index=False)  # Specify Excel file extension (.xlsx)
elif output_format == "txt":
    df.to_csv(f"{output_file_Directory}/{filename}.txt", index=False, sep=delimiter, header=False)
elif output_format == "custom":
    # Handle custom delimiters or other formats
    custom_delimiter = input("Enter custom delimiter: ")
    df.to_csv(f"{output_file_Directory}/{filename}.{output_format}", index=False, sep=custom_delimiter)
    print(f"Output saved to '{output_file_Directory}/{filename}.{output_format}'")
print(f"\n\n\n\n\nOutput saved to '{output_file_Directory}/{filename}.{output_format}'")
