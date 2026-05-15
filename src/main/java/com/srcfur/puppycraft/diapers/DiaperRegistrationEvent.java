package com.srcfur.puppycraft.diapers;

import net.neoforged.bus.api.Event;

public class DiaperRegistrationEvent extends Event {
    public DiaperRegistrationEvent(DiaperData x){
        data = x;
    }
    public DiaperData data;
}
