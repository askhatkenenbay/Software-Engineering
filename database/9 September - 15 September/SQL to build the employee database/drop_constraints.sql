alter table DEPENDENT
  drop foreign key `fk_DEPENDENT_EMPLOYEE1`;
  
alter table WORKS_ON
  drop foreign key `fk_EMPLOYEE_has_PROJECT_EMPLOYEE1`;
  
alter table WORKS_ON
  drop foreign key `fk_EMPLOYEE_has_PROJECT_PROJECT1`;
    
alter table DEPT_LOCATION  
 drop foreign key `fk_DEPT_LOCATION_DEPARTMENT1`;
    
alter table EMPLOYEE  
  drop foreign key `fk_EMPLOYEE_DEPARTMENT`;
   
alter table EMPLOYEE   
  drop foreign key `fk_EMPLOYEE_EMPLOYEE1`;
    
alter table DEPARTMENT 
  drop foreign key `fk_DEPARTMENT_EMPLOYEE1`;
  
alter table PROJECT
  drop foreign key `fk_PROJECT_DEPARTMENT1`;
   