package com.iycharge.server.admin.controller.helper;

public class PriceCompareHelper {
/*    public List<Price> LevelHighCompareLevelLow(Price price1, Price price2) {

        Price levelHigh = null;
        Price levelLow = null;
        if (price1.getType().getValue() <= price2.getType().getValue()) {
            levelHigh = price1;
            levelLow = price2;
        }
        if (price1.getType().getValue() > price2.getType().getValue()) {
            levelHigh = price2;
            levelLow = price1;
        }

        int shH = levelHigh.getPriceTimeStartHour();
        int smH = levelHigh.getPriceTimeStartMinute();
        int ehH = levelHigh.getPriceTimeEndHour();
        int emH = levelHigh.getPriceTimeEndMinute();

        int shL = levelLow.getPriceTimeStartHour();
        int smL = levelLow.getPriceTimeStartMinute();
        int ehL = levelLow.getPriceTimeEndHour();
        int emL = levelLow.getPriceTimeEndMinute();


        //相离
        if (this.fBeforeS(shH, smH, shL, smL) && this.fBeforeS(ehH, emH, shL, smL)) {
            List<Price> result = new ArrayList<>();

            result.add(levelHigh);
            result.add(levelLow);

            return result;
        }
        //相离
        if (this.fBeforeS(shL, smL, shH, smH) && this.fBeforeS(ehL, emL, shH, smH)) {
            List<Price> result = new ArrayList<>();

            result.add(levelLow);
            result.add(levelHigh);
            return result;
        }

        //H包含L
        if (this.fBeforeS(shH, smH, shL, smL) && this.fBeforeS(ehL, emL, ehH, emH)) {
            List<Price> result = new ArrayList<>();
            levelHigh.setPriceTimeEndHour(shL);
            levelHigh.setPriceTimeEndMinute(smL);


            result.add(levelHigh);
            return result;

        }
        //L包含H
        if (this.fBeforeS(shL, smL, shH, smH) && this.fBeforeS(ehH, emH, ehL, emL)) {
            List<Price> result = new ArrayList<>();
            Price price = new Price();
            price.setPriceTimeStartHour(ehH);
            price.setPriceTimeEndMinute(emH);
            price.setPrice(levelHigh.getPrice());
            price.setPriceTimeEndHour(ehL);
            price.setPriceTimeEndMinute(emL);
            price.setType(levelLow.getType());

            levelLow.setPriceTimeEndHour(shH);
            levelLow.setPriceTimeEndMinute(smH);

            result.add(levelLow);
            result.add(levelHigh);
            result.add(price);

            return result;

        }
        //相交
        if (this.fBeforeS(shH, smH, shL, smL) && this.fBeforeS(shL, smL, ehH, emH) && this.fBeforeS(ehH, emH, ehL, emL)) {

            List<Price> result = new ArrayList<>();
            levelLow.setPriceTimeStartHour(ehH);
            levelLow.setPriceTimeStartMinute(emH);

            result.add(levelHigh);
            result.add(levelLow);

            return result;
        }
        //相交
        if (this.fBeforeS(shL, smL, shH, smH) && this.fBeforeS(shH, smH, ehL, emL) && this.fBeforeS(ehL, emL, ehH, emH)) {
            List<Price> result = new ArrayList<>();

            levelLow.setPriceTimeEndHour(shH);
            levelLow.setPriceTimeEndMinute(smH);
            result.add(levelLow);
            result.add(levelHigh);

            return result;
        }
        return null;
    }

    public boolean fBeforeS(int h1, int m1, int h2, int m2) {
        if ((h1 * 60 + m1) < (h2 * 60 + m2)) {
            return true;
        }
        return false;
    }*/
}
