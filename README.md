# MadaHuffmanAssignmentJava

## Usage
The application provides a small command line interface. Import the project into your IDE of choice and run MadaHuffmanAssignment.
You will be prompted to enter a command. You can then enter one of the following commands: 

* encode
* decode

## Commands
### encode
This command will read the plain text from the text file text.txt. The programm will then create a corresponding huffman encoding
and encode the read plain text. The encoded text will be written to the binary file output.dat. The huffman code will be 
written to the text file dec_tab.txt. All files are expected to be found in the working directory of the project. 

### decode
This command will decode the encoded binary file output.dat. The data of output.dat will be converted to a binary string which
will be encoded using the huffman code provided in the file dec_tab.txt. The decoded plaintext will then be written to the
file decompressed.txt. All files are expected to be found in the working direcotry of the project. 
