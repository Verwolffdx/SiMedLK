package com.datwhite.simedlk.entity.schedule;

public class Cell {
    private int $id;
    private boolean free;
    private String room;
    private String date;
    private String time_start;
    private String time_end;

    public Cell() {
    }

    public Cell(int $id, boolean free, String room, String date, String time_start, String time_end) {
        this.$id = $id;
        this.free = free;
        this.room = room;
        this.date = date;
        this.time_start = time_start;
        this.time_end = time_end;
    }

    public int get$id() {
        return $id;
    }

    public void set$id(int $id) {
        this.$id = $id;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }


}
