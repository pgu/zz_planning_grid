package com.pgu.client;

public class Person {
    private Long id;
    private String name;
    private double start;
    private double end;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (id == null ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }

    public Person(final Long id, final String name, final double start, final double end) {
        super();
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public double getStart() {
        return start;
    }

    public void setStart(final double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(final double end) {
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", start=" + start + ", end=" + end + "]";
    }

}
