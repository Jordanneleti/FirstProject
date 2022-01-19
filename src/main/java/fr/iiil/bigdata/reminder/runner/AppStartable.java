package fr.iiil.bigdata.reminder.runner;

import fr.iiil.bigdata.reminder.services.WordCounter;

@FunctionalInterface
public interface AppStartable {

    public void start(WordCounter wordCounter);
}
