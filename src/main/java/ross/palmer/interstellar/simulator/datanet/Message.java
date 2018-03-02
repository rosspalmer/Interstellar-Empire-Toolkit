package ross.palmer.interstellar.simulator.datanet;

import ross.palmer.interstellar.simulator.engine.Simulator;
import ross.palmer.interstellar.simulator.entity.Entity;
import ross.palmer.interstellar.utilities.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class Message {

    private final long id;
    private final double timestamp;
    private final Entity fromEntity;
    private final Entity toEntity;

    private String text;
    private Runnable runnable;
    private boolean received;

    public Message(String name, Entity fromEntity, Entity toEntity) {

        id = IdGenerator.getNextId("Message");
        timestamp = Simulator.getCurrentTime();
        this.fromEntity = fromEntity;
        this.toEntity = toEntity;

        text = "";
        runnable = null;
        received = false;

        Simulator.addMessageToDataBase(this);

    }

    public void addParagraph(String paragraph) {
        text += "/n" + paragraph;
    }

    public Entity getFromEntity() {
        return fromEntity;
    }

    public Entity getToEntity() {
        return toEntity;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public String getText() {
        return text;
    }

    public boolean hasRunnable() {
        return runnable != null;
    }

    public boolean notReceived() {
        return !received;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public void setText(String text) {
        this.text = text;
    }
}
