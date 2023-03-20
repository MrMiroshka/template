package ru.miroshka.market.core.listeners;

public class LogOrders implements  Observer{

    @Override
    public void update(Subject subject, Object arg) {
        System.out.printf("Заказ создан %s\n", arg);
    }
}
