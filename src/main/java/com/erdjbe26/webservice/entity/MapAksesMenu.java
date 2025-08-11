package com.erdjbe26.webservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
class MapAksesMenuId implements Serializable {
    @Column(name = "IDAkses")
    private Long idAkses;

    @Column(name = "IDMenu")
    private Long idMenu;
}

@Entity
@Table(name = "MapAksesMenu")
@Getter
@Setter
public class MapAksesMenu {

    @EmbeddedId
    private MapAksesMenuId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idAkses")
    @JoinColumn(name = "IDAkses")
    private MstAkses akses;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idMenu")
    @JoinColumn(name = "IDMenu")
    private MstMenu menu;
}
