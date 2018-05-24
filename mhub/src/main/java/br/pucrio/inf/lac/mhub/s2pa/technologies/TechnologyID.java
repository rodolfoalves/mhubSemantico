package br.pucrio.inf.lac.mhub.s2pa.technologies;

public enum TechnologyID {

    BLE(1), INTERNAL(100), BT(2);

    public int id;

    TechnologyID(int id) {
        this.id = id;
    }

}
