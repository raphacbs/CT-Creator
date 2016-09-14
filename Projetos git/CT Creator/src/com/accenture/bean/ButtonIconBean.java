/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.bean;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author brucilin.de.gouveia
 */
public class ButtonIconBean {

    ImageIcon iconBntAddStepDefault;
    ImageIcon iconBntAddCtDefault;
    ImageIcon iconBntAddNewStep;
    ImageIcon iconBntAddNewCt;
    ImageIcon iconBntRemoveStep;
    ImageIcon iconBntRemoveCt;
    ImageIcon iconBntMoveStepTop;
    ImageIcon iconBntMoveStepBottom;
    ImageIcon iconBntCacelAction;
    ImageIcon iconBntCacelActionMinimum;
    ImageIcon iconBntConfirmAction;
    ImageIcon iconBntFilter;
    ImageIcon iconBntSaveMinimum;
    ImageIcon iconBntSearchCt;
    Icon iconPriorityHight;
    Icon iconPriorityMedium;
    Icon iconPriorityLow;
    ImageIcon iconParameter;
    ImageIcon iconNext;
    ImageIcon iconPrevious;
    ImageIcon iconOrdem;
    ImageIcon iconDuplicate;
    ImageIcon iconReplace;
    ImageIcon iconBntDate;

    public ButtonIconBean() {

        iconBntAddStepDefault = new ImageIcon("./res/bnt/ic_add_step_default.png");
        iconBntAddNewStep = new ImageIcon("./res/bnt/ic_add_new_step2.png");
        iconBntRemoveStep = new ImageIcon("./res/bnt/ic_remove_step2.png");
        iconBntMoveStepTop = new ImageIcon("./res/bnt/ic_top_step.png");
        iconBntMoveStepBottom = new ImageIcon("./res/bnt/ic_bottom_step.png");
        iconBntCacelAction = new ImageIcon("./res/bnt/ic_cancel_operation.png");
        iconBntCacelActionMinimum = new ImageIcon("./res/bnt/ic_cancel_operation_minimum.png");
        iconBntConfirmAction = new ImageIcon("./res/bnt/ic_confirm_operation.png");
        iconBntFilter = new ImageIcon("./res/bnt/ic_filter_ct.png");
        iconBntSaveMinimum = new ImageIcon("./res/bnt/ic_save_minimum.png");
        iconBntAddCtDefault = new ImageIcon("./res/bnt/ic_add_ct_default.png");
        iconBntAddNewCt = new ImageIcon("./res/bnt/ic_add_new_ct.png");
        iconBntRemoveCt = new ImageIcon("./res/bnt/ic_remove_ct.png");
        iconBntSearchCt = new ImageIcon("./res/bnt/ic_search_ct.png");
        iconBntAddStepDefault = new ImageIcon("./res/bnt/ic_add_step_default2.png");
        iconPriorityHight = new ImageIcon("./res/bnt/ic_priority_hight_ct.png");
        iconPriorityMedium = new ImageIcon("./res/bnt/ic_priority_medium_ct.png");
        iconPriorityLow = new ImageIcon("./res/bnt/ic_priority_low_ct.png");
        iconParameter = new ImageIcon("./res/bnt/ic_parametro.png");
        iconPrevious = new ImageIcon("./res/bnt/ic_previous.png");
        iconNext = new ImageIcon("./res/bnt/ic_next.png");
        iconOrdem = new ImageIcon("./res/bnt/ic_ordenacao.png");
        iconDuplicate = new ImageIcon("./res/bnt/ic_duplicar.png");
        iconReplace = new ImageIcon("./res/bnt/ic_replace.png");
        iconBntDate = new ImageIcon("./res/bnt/ic_date.png");
    }

    public ImageIcon getIconBntDate() {
        return iconBntDate;
    }

    public void setIconBntDate(ImageIcon iconBntDate) {
        this.iconBntDate = iconBntDate;
    }
    
    public ImageIcon getIconReplace() {
        return iconReplace;
    }

    public void setIconReplace(ImageIcon iconReplace) {
        this.iconReplace = iconReplace;
    }
    
    public ImageIcon getIconDuplicate() {
        return iconDuplicate;
    }

    public void setIconDuplicate(ImageIcon iconDuplicate) {
        this.iconDuplicate = iconDuplicate;
    }
    
    

    public ImageIcon getIconOrdem() {
        return iconOrdem;
    }

    public void setIconOrdem(ImageIcon iconOrdem) {
        this.iconOrdem = iconOrdem;
    }

    
    
    public ImageIcon getIconNext() {
        return iconNext;
    }

    public void setIconNext(ImageIcon iconNext) {
        this.iconNext = iconNext;
    }

    public ImageIcon getIconPrevious() {
        return iconPrevious;
    }

    public void setIconPrevious(ImageIcon iconPrevious) {
        this.iconPrevious = iconPrevious;
    }
    
    

    public ImageIcon getIconParameter() {
        return iconParameter;
    }

    public void setIconParameter(ImageIcon iconParameter) {
        this.iconParameter = iconParameter;
    }

    public ImageIcon getIconBntAddStepDefault() {
        return iconBntAddStepDefault;
    }

    public void setIconBntAddStepDefault(ImageIcon iconBntAddStepDefault) {
        this.iconBntAddStepDefault = iconBntAddStepDefault;
    }

    public ImageIcon getIconBntAddCtDefault() {
        return iconBntAddCtDefault;
    }

    public void setIconBntAddCtDefault(ImageIcon iconBntAddCtDefault) {
        this.iconBntAddCtDefault = iconBntAddCtDefault;
    }

    public ImageIcon getIconBntAddNewStep() {
        return iconBntAddNewStep;
    }

    public void setIconBntAddNewStep(ImageIcon iconBntAddNewStep) {
        this.iconBntAddNewStep = iconBntAddNewStep;
    }

    public ImageIcon getIconBntAddNewCt() {
        return iconBntAddNewCt;
    }

    public void setIconBntAddNewCt(ImageIcon iconBntAddNewCt) {
        this.iconBntAddNewCt = iconBntAddNewCt;
    }

    public ImageIcon getIconBntRemoveStep() {
        return iconBntRemoveStep;
    }

    public void setIconBntRemoveStep(ImageIcon iconBntRemoveStep) {
        this.iconBntRemoveStep = iconBntRemoveStep;
    }

    public ImageIcon getIconBntRemoveCt() {
        return iconBntRemoveCt;
    }

    public void setIconBntRemoveCt(ImageIcon iconBntRemoveCt) {
        this.iconBntRemoveCt = iconBntRemoveCt;
    }

    public ImageIcon getIconBntMoveStepTop() {
        return iconBntMoveStepTop;
    }

    public void setIconBntMoveStepTop(ImageIcon iconBntMoveStepTop) {
        this.iconBntMoveStepTop = iconBntMoveStepTop;
    }

    public ImageIcon getIconBntMoveStepBottom() {
        return iconBntMoveStepBottom;
    }

    public void setIconBntMoveStepBottom(ImageIcon iconBntMoveStepBottom) {
        this.iconBntMoveStepBottom = iconBntMoveStepBottom;
    }

    public ImageIcon getIconBntCacelAction() {
        return iconBntCacelAction;
    }

    public void setIconBntCacelAction(ImageIcon iconBntCacelAction) {
        this.iconBntCacelAction = iconBntCacelAction;
    }

    public ImageIcon getIconBntCacelActionMinimum() {
        return iconBntCacelActionMinimum;
    }

    public void setIconBntCacelActionMinimum(ImageIcon iconBntCacelActionMinimum) {
        this.iconBntCacelActionMinimum = iconBntCacelActionMinimum;
    }

    public ImageIcon getIconBntConfirmAction() {
        return iconBntConfirmAction;
    }

    public void setIconBntConfirmAction(ImageIcon iconBntConfirmAction) {
        this.iconBntConfirmAction = iconBntConfirmAction;
    }

    public ImageIcon getIconBntFilter() {
        return iconBntFilter;
    }

    public void setIconBntFilter(ImageIcon iconBntFilter) {
        this.iconBntFilter = iconBntFilter;
    }

    public ImageIcon getIconBntSaveMinimum() {
        return iconBntSaveMinimum;
    }

    public void setIconBntSaveMinimum(ImageIcon iconBntSaveMinimum) {
        this.iconBntSaveMinimum = iconBntSaveMinimum;
    }

    public ImageIcon getIconBntSearchCt() {
        return iconBntSearchCt;
    }

    public void setIconBntSearchCt(ImageIcon iconBntSearchCt) {
        this.iconBntSearchCt = iconBntSearchCt;
    }

    public Icon getIconPriorityHight() {
        return iconPriorityHight;
    }

    public void setIconPriorityHight(Icon iconPriorityHight) {
        this.iconPriorityHight = iconPriorityHight;
    }

    public Icon getIconPriorityMedium() {
        return iconPriorityMedium;
    }

    public void setIconPriorityMedium(Icon iconPriorityMedium) {
        this.iconPriorityMedium = iconPriorityMedium;
    }

    public Icon getIconPriorityLow() {
        return iconPriorityLow;
    }

    public void setIconPriorityLow(Icon iconPriorityLow) {
        this.iconPriorityLow = iconPriorityLow;
    }

}
