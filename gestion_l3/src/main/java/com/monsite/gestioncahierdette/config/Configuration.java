package com.monsite.gestioncahierdette.config;

public class Configuration {
    public enum ModeStockage { LISTE, BASE_DE_DONNEES }

    private static ModeStockage modeStockage = ModeStockage.BASE_DE_DONNEES; // Par défaut

    public static ModeStockage getModeStockage() {
        return modeStockage;
    }

    public static void setModeStockage(ModeStockage mode) {
        modeStockage = mode;
    }
}
