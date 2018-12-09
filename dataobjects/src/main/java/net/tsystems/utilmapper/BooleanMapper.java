package net.tsystems.utilmapper;

import org.mapstruct.Mapper;

@Mapper
public class BooleanMapper {
    public boolean asBoolean(byte byteVal) {
        return byteVal != 0;
    }

    public byte asByte (boolean boolVal) {
        return boolVal ? (byte)1 : 0;
    }
}
