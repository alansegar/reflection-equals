package com.agilepeak.blog;

import java.util.Objects;

public abstract class AbstractBean {

    protected String id;

    protected long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractBean that = (AbstractBean) o;
        return version == that.version &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}
