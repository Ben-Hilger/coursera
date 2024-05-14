package io.collective

import java.time.Clock

class ExpirableEntryK {

  val key: Any?
  val value: Any?
  val expiresAt: Long

  var next: ExpirableEntryK? = null 

  constructor(
      key: Any?, 
      value: Any?, 
      expiresAt: Long
  ) {
      this.key = key       
      this.value = value
      this.expiresAt = expiresAt
  }    

  fun isValid(clock: Clock): Boolean {
      return clock.millis() <= expiresAt;
  }
}