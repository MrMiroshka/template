package ru.miroshka.market.core.listeners;

public interface Observer {
    void update(Subject subject, Object arg);
}

