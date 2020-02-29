package com.rohov.internal.exception;
//TODO in upper case
public interface ErrorMessages {
    String WRONG_CREDENTIAL = "Wrong credentials, check your input !";
    String TYPE_OF_REQUEST_ACTION = "Wrong type of action !";

    String COMPANY_WRONG_ID = "Can not find company with id : %d";
    String COMPANY_WRONG_NAME = "Can not find company with name : %s";
    String COMPANY_WITH_NAME_ALREADY_EXIST = "Company with name ' %s ' already exist";

    String EMPLOYEE_POSITION_WRONG_ID = "Can not find employee position by id : %d";
    String EMPLOYEE_POSITION_WRONG_NAME = "Can not find employee position by name : %s";

    String USER_WRONG_EMAIL = "User with email  %s not found !";
    String USER_WRONG_ID = "User with id  %d not found !";
    String USER_WRONG_TOKEN = "Couldn't find user by token : %s";

    String PROJECT_WRONG_ID = "Can not find project with id : %d";
    String PROJECT_NOT_OWNED_BY_COMPANY = "This project is not owned by the company with id : %d";

    String ROLE_WRONG_NAME = "Can not find role with name : %s";

    String PERMISSION_WRONG_NAME = "Can not find permission with name : %s";

    String TASK_WRONG_ID = "Can not find task with id : %d";

    String TASK_STATUS_WRONG_ID = "Can not find task status with id : %d";
    String TASK_STATUS_WRONG_NAME = "Can not find task status with name : %s";

    String TASK_CHECKLIST_WRONG_ID = "Can not find checklist status with id : %d";
}
