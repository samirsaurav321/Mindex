 For the compensation i have used the following Json schema:

''' 

{
 "type":"Compensation",
  "properties": {
    "employee": {
      "type": "Employee"
    },
    "salary": {
          "type": "integer"
    },
    "effectiveDate": {
      "type": "string",
      "format": "date-time"
    }
    }
  }
}
'''