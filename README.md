# TestePratico: Java practical test

Console program written for a Java technical test: manage a list of employees and produce a series of reports.

## What the program does

1. Registers the employees from the given table
2. Removes one employee from the list
3. Prints all employees with formatted dates (`dd/MM/yyyy`) and salaries
4. Applies a 10% raise to every salary
5. Groups employees by role
6. Lists who has a birthday in October and December
7. Finds the oldest employee
8. Sorts employees by name
9. Sums all salaries
10. Calculates how many minimum wages each employee earns

## Concepts used

- Inheritance (`Funcionario` extends `Pessoa`)
- `BigDecimal` for money, with explicit rounding
- `LocalDate`, `Period` and `DateTimeFormatter` for dates
- Collections: lists, maps for grouping, sorting with comparators

## Running

```bash
cd src
javac *.java
java Principal
```

Requires Java 8 or newer. Output is in Portuguese.
