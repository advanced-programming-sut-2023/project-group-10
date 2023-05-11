package org.example.controller;

import org.example.model.Stronghold;
import org.example.model.game.envirnmont.Coordinate;
import org.example.model.game.units.Engineer;
import org.example.model.game.units.MilitaryUnit;
import org.example.model.game.units.SiegeEquipment;
import org.example.view.MountEquipmentMenuMessages;

import java.util.ArrayList;

public class MountEquipmentMenuController {
    private static SiegeEquipment siegeEquipment;
    private static int index;
    private static Engineer[] selectedEngineers;

    public static Engineer[] getSelectedEngineers() {
        return selectedEngineers;
    }

    public static void setup(int count, SiegeEquipment equipment) {
        siegeEquipment = equipment;
        index = 0;
        MountEquipmentMenuController.selectedEngineers = new Engineer[count];
    }

    public static int getUnselectedEngineersCount() {
        return selectedEngineers.length - index;
    }

    public static MountEquipmentMenuMessages selectEngineers(Coordinate coordinate) {
        ArrayList<MilitaryUnit> units = Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(coordinate).getSelectableMilitaryUnitsByGovernment(Stronghold.getCurrentBattle().getGovernmentAboutToPlay());
        ArrayList<Engineer> engineers = new ArrayList<>();
        for (MilitaryUnit unit : units)
            if (unit instanceof Engineer && !((Engineer) unit).isOnBoilingDuty())
                engineers.add((Engineer) unit);
        if (engineers.size() == 0) return MountEquipmentMenuMessages.NO_ENGINEERS;
        if (engineers.contains(engineers.get(0))) return MountEquipmentMenuMessages.ALREADY_SELECTED;
        for (int i = 0; i < engineers.size() && index < selectedEngineers.length; i++, index++)
            selectedEngineers[index] = engineers.get(i);
        return MountEquipmentMenuMessages.SELECTION_SUCCESSFUL;
    }

    public static void completeMountingProcess() {
        siegeEquipment.addEngineers(selectedEngineers);
        for (Engineer selectedEngineer : selectedEngineers) {
            selectedEngineer.stop();
            Stronghold.getCurrentBattle().getBattleMap().getBlockByRowAndColumn(selectedEngineer.getPosition()).removeUnit(selectedEngineer);
        }
    }
}
