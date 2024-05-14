package io.collective;

import java.time.Clock;

class ExpirableEntry {

  private Object key;
  private Object value;

  private long expiresAt;

  public ExpirableEntry next = null;

  protected ExpirableEntry(Object key, Object value, long expiresAt) {
      this.key = key;
      this.value = value;
      this.expiresAt = expiresAt;
  }

  public boolean isValid(Clock clock) {
      return clock.millis() <= expiresAt;
  }

  public boolean hasKey(Object key) {
      return this.key.equals(key);
  }

  public Object getValue() {
      return value;
  }

}
