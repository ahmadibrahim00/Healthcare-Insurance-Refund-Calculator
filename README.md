# Healthcare Insurance Refund Calculator

**A backend application designed to process and calculate healthcare insurance claim refunds.**

---

## 🎯 Project Overview

This application validates and processes insurance claims, calculating refunds based on specific contract rules. It accepts JSON input files containing claim data and generates JSON output files with calculated refunds or error messages.

---

## 🛠️ Technologies Used

- **Programming Language**: Java (JDK 11+)
- **Development Environment**: IntelliJ IDEA
- **Data Format**: JSON (UTF-8)

---

## 🚀 Key Features

1. **Input Validation**:
   - Ensures client numbers are 6 digits.
   - Verifies contracts are one of the allowed types: A, B, C, D.
   - Checks claim dates are within the specified month (ISO 8601 format).
   - Validates claim categories and monetary amounts.

2. **Refund Calculation**:
   - Calculates refunds based on contract-specific rules for different claim categories.
   - Supports four contract types (A, B, C, D) with varying refund percentages and caps.

3. **Error Handling**:
   - Produces a JSON error message for invalid input data.

---

## 📋 Input/Output Example

### **Input File**:
```json
{
    "client": "100323",
    "contrat": "A",
    "mois": "2022-01",
    "reclamations": [
        { "soin": 100, "date": "2022-01-11", "montant": "234.00$" },
        { "soin": 200, "date": "2022-01-13", "montant": "90.00$" }
    ]
}
```
### **Output File**:
```json
{
    "client": "100323",
    "mois": "2022-01",
    "remboursements": [
        { "soin": 100, "date": "2022-01-11", "montant": "58.50$" },
        { "soin": 200, "date": "2022-01-13", "montant": "22.50$" }
    ]
}
```
---
## 💻 Installation and Usage
### 1. Clone the Repo :
```bash
git clone https://gitlab.info.uqam.ca/<your-repo-url>
cd <project-directory>
```
### 2. Compile and Run : 
```bash
javac -d bin src/*.java
java -jar Refund.jar inputfile.json outputfile.json
```

### 3. Error handling :
```json
{ "message": "Données invalides" }
```
