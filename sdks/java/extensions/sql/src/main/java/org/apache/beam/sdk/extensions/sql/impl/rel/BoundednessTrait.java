/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.beam.sdk.extensions.sql.impl.rel;

import org.apache.beam.sdk.values.PCollection;
import org.apache.calcite.plan.RelOptPlanner;
import org.apache.calcite.plan.RelTrait;
import org.apache.calcite.plan.RelTraitDef;

/** Convertion for Beam SQL. */
public interface BoundednessTrait extends RelTrait {
  PCollection.IsBounded isBounded();

  default RelTraitDef getTraitDef() {
    return BoundednessTraitDef.INSTANCE;
  }

  default void register(RelOptPlanner planner) {}

  BoundednessTrait BOUNDED =
      new BoundednessTrait() {
        @Override
        public PCollection.IsBounded isBounded() {
          return PCollection.IsBounded.BOUNDED;
        }

        @Override
        public boolean satisfies(RelTrait trait) {
          return trait == this || trait == UNBOUNDED;
        }
      };

  BoundednessTrait UNBOUNDED =
      new BoundednessTrait() {
        @Override
        public PCollection.IsBounded isBounded() {
          return PCollection.IsBounded.UNBOUNDED;
        }

        @Override
        public boolean satisfies(RelTrait trait) {
          return trait == this;
        }
      };

  static BoundednessTrait of(PCollection.IsBounded isBounded) {
    switch (isBounded) {
      case BOUNDED:
        return BoundednessTrait.BOUNDED;
      case UNBOUNDED:
        return BoundednessTrait.UNBOUNDED;
      default:
        throw new IllegalArgumentException(String.format("Unknown boundedness: %s", isBounded));
    }
  }
}
