**Repository:** [Ldeane25/java](https://github.com/Ldeane25/java)  

A Java application that converts words to numeric codes and vice versa using customizable mappings.

---

## 🚀 Features  
- **Two-way conversion**: Encode (words → numbers) and decode (numbers → words)  
- **Custom mappings**: Load your own word-number pairs from CSV files  
- **Progress tracking**: Visual progress bar during file processing  
- **User-friendly**: Interactive console menu  
- **Flexible I/O**: Specify custom input/output file paths  

## 📦 Prerequisites  
- Java 8+  
- Maven (for building)  

## 🛠️ Installation  
1. Clone the repository:  
   ```bash  
   git clone https://github.com/Ldeane25/java.git  
Navigate to the project:

bash
Copy
cd java  
🏃‍♂️ Running the Program
Option 1: Direct execution
bash
Copy
java -cp target/your-project.jar ie.atu.sw.Runner  
Option 2: Build with Maven first
bash
Copy
mvn clean package  
java -jar target/your-project.jar  
🎮 How to Use
Default mapping file: encodings-10000.csv (included in resources)

Menu options:

Copy
1 - Change mapping file  
2 - Set input text file  
3 - Set output file (default: out.txt)  
4 - Toggle encode/decode mode  
5 - Execute encoding  
6 - Execute decoding  
? - Exit  
📂 File Formats
Mapping File (CSV)
csv
Copy
word1,100  
word2,200  
Example Workflow
Input text:

Copy
hello world  
test phrase  
Encoded output:

Copy
42 99  
0 0  
(Unknown words become "0")

🛠️ Building the Project
bash
Copy
mvn clean package  
