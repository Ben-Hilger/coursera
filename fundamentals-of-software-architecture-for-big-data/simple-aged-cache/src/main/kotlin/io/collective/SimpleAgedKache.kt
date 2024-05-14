package io.collective

import java.time.Clock

class SimpleAgedKache { 

    var root: ExpirableEntryK? = null
    var clock: Clock = Clock.systemUTC()
    
    constructor(clock: Clock?) {
        if (clock == null) {
            this.clock = Clock.systemUTC()
        } else {
            this.clock = clock;    
        }
    }

    constructor() {}

    fun put(key: Any?, value: Any?, retentionInMillis: Int) {
        val expiresAt = this.clock.millis() + retentionInMillis 
        val newEntry = ExpirableEntryK(key, value, expiresAt)
        if (root == null) {
            root = newEntry 
        }
        newEntry.next = root
        root = newEntry
    }

    fun isEmpty(): Boolean {
        return size() == 0
    }

    fun size(): Int {
        var size: Int = 0
        var current: ExpirableEntryK? = root
        while (current != null) {
            if (current.isValid(clock))
                size++
            current = current.next
        }
        return size
    }

    fun get(key: Any?): Any? {
        var current: ExpirableEntryK? = root
        while (current != null) {
            if (current.key == key) {
                return current.value
            }   
            current = current.next
        }
        return null
    }
}

