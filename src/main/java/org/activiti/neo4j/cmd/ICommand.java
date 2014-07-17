/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.neo4j.cmd;


import org.activiti.neo4j.CommandContextNeo4j;

/**
 * Command interface represents an implementation of any one meaningful operation, usually
 * with a database. This is analog of {@link org.activiti.engine.impl.interceptor.Command}
 * interface, but it is used only by activiti-neo4j Services.
 *
 * @author Joram Barrez
 */
public interface ICommand<T> {

    /**
     * Execute an activiti-neo4j specific command within the context.
     *
     * @param commandContextNeo4j
     */
    T execute(CommandContextNeo4j<T> commandContextNeo4j);
}