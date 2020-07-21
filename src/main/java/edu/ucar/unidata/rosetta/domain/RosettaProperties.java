/*
 * Copyright (c) 2012-2020 University Corporation for Atmospheric Research/Unidata.
 * See LICENSE for license information.
 */

package edu.ucar.unidata.rosetta.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * An object that represents an arbitrary rosetta configuration property. This object is used to
 * retrieve persisted property values. Lombok automatic generation of noop constructor, getters, setters, equals,
 * hashCode and toString.
 *
 * @author oxelson@ucar.edu
 */
@Data
@NoArgsConstructor
public class RosettaProperties {
  private int id;
  private String propertyKey;
  private String propertyValue;
}

