/*
 * Copyright (c) 2012-2020 University Corporation for Atmospheric Research/Unidata.
 * See LICENSE for license information.
 */

package edu.ucar.unidata.rosetta.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Object representing a variable. Lombok automatic generation of noop constructor, getters, setters, equals, hashCode
 * and toString.
 *
 * @author oxelson
 */
@Data
@NoArgsConstructor
public class Variable {

  private int variableId;
  private String wizardDataId;
  private int columnNumber;
  private String variableName;
  private String metadataType;
  private String metadataTypeStructure;
  private String verticalDirection;
  private String metadataValueType;
  private List<VariableMetadata> requiredMetadata = new ArrayList<>();
  private List<VariableMetadata> recommendedMetadata = new ArrayList<>();
  private List<VariableMetadata> additionalMetadata = new ArrayList<>();
}
