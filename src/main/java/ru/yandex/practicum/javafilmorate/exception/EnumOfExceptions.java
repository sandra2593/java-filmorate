package ru.yandex.practicum.javafilmorate.exception;

public enum EnumOfExceptions {

    NO_FILM ("Такой фильма нет в списке"),
    DOUBLE_FILM ("Такой фильм уже есть"),
    RELEASE_DATE ("Дата релиза — не раньше 28 декабря 1895 года"),
    NO_USER ("Пользователь не найден"),
    DOUBLE_USER ("Такой пользователь уже есть"),
    NO_SPACE_IN_USER ("Логин не может содержать пробелы"),
    NOT_IN_LIST ("Такой юзера нет в списке");

    private String exp;

    EnumOfExceptions(String exp) {
        this.exp = exp;
    }

    public String getExp() {
        return exp;
    }

}
