/**
 * Copyright (c) 2002-2014 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.cypher.internal.compiler.v2_2.planner.logical.plans

import org.neo4j.cypher.InternalException
import org.neo4j.cypher.internal.compiler.v2_2.planner.PlannerQuery
import org.neo4j.cypher.internal.compiler.v2_2.symbols._

case class SingleRow(coveredIds: Set[IdName])(val solved: PlannerQuery)
                    (val typeInfo: Map[String, CypherType] = coveredIds.map( id => id.name -> CTNode).toMap) extends LogicalLeafPlan {
  def availableSymbols = coveredIds

  override def dup(children: Seq[AnyRef]) = children match {
    case newCoveredIds :: Nil =>
      copy(newCoveredIds.asInstanceOf[Set[IdName]])(solved)(typeInfo).asInstanceOf[this.type]
    case _                    =>
      throw new InternalException("Did not expect this code path to be used.")
  }
}
