package ross.palmer.interstellar.simulator.datanet;

import ross.palmer.interstellar.simulator.engine.Simulator;
import ross.palmer.interstellar.simulator.entity.Entity;
import ross.palmer.interstellar.utilities.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Message {

    private final long id;
    private final double sentTimestamp;
    private double receivedTimestamp;
    private final Entity fromEntity;
    private final Entity toEntity;

    private String text;
    private Runnable runnable;
    private boolean received;

    public Message(String name, Entity fromEntity, Entity toEntity) {

        id = IdGenerator.getNextId("Message");
        sentTimestamp = Simulator.getCurrentTime();
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

    public String getText() {
        return text;
    }

    public boolean notReceived() {
        return !received;
    }

    public double getReceivedTimestamp() {
        return receivedTimestamp;
    }

    public double getSentTimestamp() {
        return sentTimestamp;
    }

    public void receiveMessage() {
        this.received = true;
        receivedTimestamp = Simulator.getCurrentTime();
        if (runnable != null)
            runnable.run();
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
