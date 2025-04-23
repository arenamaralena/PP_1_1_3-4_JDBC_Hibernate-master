package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        //Создание таблицы User(ов)
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        //Добавление 4 User(ов) в таблицу с данными на свой выбор.
        userService.saveUser("Name", "LastName", (byte) 18);
        userService.saveUser("Anna", "Petrova", (byte) 20);
        userService.saveUser("Petr", "Ivanov", (byte) 40);
        userService.saveUser("Ivan", "Ivanov", (byte) 35);
        //Получение всех User из базы и вывод в консоль
        System.out.println(userService.getAllUsers());
        //Очистка таблицы User(ов)
        userService.cleanUsersTable();
        //Удаление таблицы
        userService.dropUsersTable();
    }
}
