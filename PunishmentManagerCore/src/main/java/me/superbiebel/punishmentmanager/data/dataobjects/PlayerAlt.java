package me.superbiebel.punishmentmanager.data.dataobjects;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlayerAlt {

    private UUID playerUUID;
    private UUID altUUID;
}
