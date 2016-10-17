/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accenture.control;

import com.accenture.bean.FlowPropertiesBean;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author brucilin.de.gouveia
 */
public class FlowPropertiesController {

    private FlowPropertiesBean flowBean;

    public FlowPropertiesController(String idTestCase, String system) throws IOException {

        this.flowBean = new FlowPropertiesBean(idTestCase, system);
    }

    /**
     *
     * @return
     */
    public boolean addIdTestCase(String id) {
        Properties file = flowBean.getFileProperties();
        file.put(id, "");
        return false;
    }

    /**
     *
     * @param idFlow
     */
    public void addFlow(String idFlow) {

    }

    /**
     *
     */
    public void removeFlow() {
        Properties file = flowBean.getFileProperties();

    }

    /**
     *
     * @return
     */
    public boolean isFlowAssociated() {

        return !this.flowBean.getIdFlows().isEmpty();
    }

}
