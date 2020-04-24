package com.tancheng.carbonchain.activities.asset.wallet.domain;

/**
 * created by tc_collin on 2020/3/27 14:34
 * Description:
 * version: 1.0
 */
public class VerifyMnemonicWordTag {
    private String mnemonicWord;
    private boolean isSelected;

    public String getMnemonicWord() {
        return mnemonicWord;
    }

    public void setMnemonicWord(String mnemonicWord) {
        this.mnemonicWord = mnemonicWord;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
