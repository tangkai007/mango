/*
 * Copyright 2014 mango.jfaster.org
 *
 * The Mango Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.jfaster.mango.operator;

import org.jfaster.mango.datasource.DataSourceFactory;
import org.jfaster.mango.datasource.MultipleDatabaseDataSourceFactory;
import org.jfaster.mango.datasource.SimpleDataSourceFactory;
import org.jfaster.mango.exception.IncorrectReturnTypeException;
import org.jfaster.mango.reflect.MethodDescriptor;
import org.jfaster.mango.reflect.ParameterDescriptor;
import org.jfaster.mango.reflect.ReturnDescriptor;
import org.jfaster.mango.reflect.TypeToken;
import org.jfaster.mango.sharding.DatabaseShardingStrategy;
import org.jfaster.mango.sharding.ModHundredTableShardingStrategy;
import org.jfaster.mango.support.*;
import org.jfaster.mango.support.model4table.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author ash
 */
public class BatchUpdateOperatorTest {

    @Test
    public void testExecuteReturnVoid() throws Exception {
        TypeToken<List<User>> pt = new TypeToken<List<User>>() {};
        TypeToken<Void> rt = TypeToken.of(void.class);
        String srcSql = "update user set name=:1.name where id=:1.id";
        Operator operator = getOperator(pt, rt, srcSql);

        final int[] expectedInts = new int[] {1, 2};
        StatsCounter sc = new StatsCounter();
        operator.setStatsCounter(sc);
        operator.setJdbcOperations(new JdbcOperationsAdapter() {
            @Override
            public int[] batchUpdate(DataSource ds, String sql, List<Object[]> batchArgs) {
                String descSql = "update user set name=? where id=?";
                assertThat(sql, equalTo(descSql));
                assertThat(batchArgs.size(), equalTo(2));
                assertThat(batchArgs.get(0)[0], equalTo((Object) "ash"));
                assertThat(batchArgs.get(0)[1], equalTo((Object) 100));
                assertThat(batchArgs.get(1)[0], equalTo((Object) "lucy"));
                assertThat(batchArgs.get(1)[1], equalTo((Object) 200));
                return expectedInts;
            }
        });

        List<User> users = Arrays.asList(new User(100, "ash"), new User(200, "lucy"));
        Object actual = operator.execute(new Object[]{users});
        assertThat(actual, nullValue());
    }

    @Test
    public void testExecuteReturnInt() throws Exception {
        TypeToken<List<User>> pt = new TypeToken<List<User>>() {};
        TypeToken<Integer> rt = TypeToken.of(int.class);
        String srcSql = "update user set name=:1.name where id=:1.id";
        Operator operator = getOperator(pt, rt, srcSql);

        final int[] expectedInts = new int[] {1, 2};
        StatsCounter sc = new StatsCounter();
        operator.setStatsCounter(sc);
        operator.setJdbcOperations(new JdbcOperationsAdapter() {
            @Override
            public int[] batchUpdate(DataSource ds, String sql, List<Object[]> batchArgs) {
                String descSql = "update user set name=? where id=?";
                assertThat(sql, equalTo(descSql));
                assertThat(batchArgs.size(), equalTo(2));
                assertThat(batchArgs.get(0)[0], equalTo((Object) "ash"));
                assertThat(batchArgs.get(0)[1], equalTo((Object) 100));
                assertThat(batchArgs.get(1)[0], equalTo((Object) "lucy"));
                assertThat(batchArgs.get(1)[1], equalTo((Object) 200));
                return expectedInts;
            }
        });

        List<User> users = Arrays.asList(new User(100, "ash"), new User(200, "lucy"));
        int actual = (Integer) operator.execute(new Object[]{users});
        assertThat(actual, is(3));
    }

    @Test
    public void testExecuteReturnIntArray() throws Exception {
        TypeToken<List<User>> pt = new TypeToken<List<User>>() {};
        TypeToken<int[]> rt = TypeToken.of(int[].class);
        String srcSql = "update user set name=:1.name where id=:1.id";
        Operator operator = getOperator(pt, rt, srcSql);

        final int[] expectedInts = new int[] {1, 2};
        StatsCounter sc = new StatsCounter();
        operator.setStatsCounter(sc);
        operator.setJdbcOperations(new JdbcOperationsAdapter() {
            @Override
            public int[] batchUpdate(DataSource ds, String sql, List<Object[]> batchArgs) {
                String descSql = "update user set name=? where id=?";
                assertThat(sql, equalTo(descSql));
                assertThat(batchArgs.size(), equalTo(2));
                assertThat(batchArgs.get(0)[0], equalTo((Object) "ash"));
                assertThat(batchArgs.get(0)[1], equalTo((Object) 100));
                assertThat(batchArgs.get(1)[0], equalTo((Object) "lucy"));
                assertThat(batchArgs.get(1)[1], equalTo((Object) 200));
                return expectedInts;
            }
        });

        List<User> users = Arrays.asList(new User(100, "ash"), new User(200, "lucy"));
        int[] actualInts = (int[]) operator.execute(new Object[]{users});
        assertThat(Arrays.toString(actualInts), equalTo(Arrays.toString(expectedInts)));
    }

    @Test
    public void testExecuteReturnIntegerArray() throws Exception {
        TypeToken<List<User>> pt = new TypeToken<List<User>>() {};
        TypeToken<Integer[]> rt = TypeToken.of(Integer[].class);
        String srcSql = "update user set name=:1.name where id=:1.id";
        Operator operator = getOperator(pt, rt, srcSql);

        final int[] expectedInts = new int[] {1, 2};
        StatsCounter sc = new StatsCounter();
        operator.setStatsCounter(sc);
        operator.setJdbcOperations(new JdbcOperationsAdapter() {
            @Override
            public int[] batchUpdate(DataSource ds, String sql, List<Object[]> batchArgs) {
                String descSql = "update user set name=? where id=?";
                assertThat(sql, equalTo(descSql));
                assertThat(batchArgs.size(), equalTo(2));
                assertThat(batchArgs.get(0)[0], equalTo((Object) "ash"));
                assertThat(batchArgs.get(0)[1], equalTo((Object) 100));
                assertThat(batchArgs.get(1)[0], equalTo((Object) "lucy"));
                assertThat(batchArgs.get(1)[1], equalTo((Object) 200));
                return expectedInts;
            }
        });

        List<User> users = Arrays.asList(new User(100, "ash"), new User(200, "lucy"));
        Integer[] actualInts = (Integer[]) operator.execute(new Object[]{users});
        assertThat(Arrays.toString(actualInts), equalTo(Arrays.toString(expectedInts)));
    }

    @Test
    public void testExecuteMulti() throws Exception {
        TypeToken<List<User>> pt = new TypeToken<List<User>>() {};
        TypeToken<int[]> rt = TypeToken.of(int[].class);
        String srcSql = "update #table set name=:1.name where id=:1.id";
        Operator operator = getOperator2(pt, rt, srcSql);

        StatsCounter sc = new StatsCounter();
        operator.setStatsCounter(sc);
        operator.setJdbcOperations(new JdbcOperationsAdapter() {
            @Override
            public int[] batchUpdate(DataSource ds, List<String> sqls, List<Object[]> batchArgs) {
                List<String> descSqls = Arrays.asList("update user_10 set name=? where id=?",
                        "update user_20 set name=? where id=?");
                assertThat(sqls, equalTo(descSqls));
                assertThat(batchArgs.size(), equalTo(2));
                assertThat(batchArgs.get(0)[0], equalTo((Object) "ash"));
                assertThat(batchArgs.get(0)[1], equalTo((Object) 10));
                assertThat(batchArgs.get(1)[0], equalTo((Object) "lucy"));
                assertThat(batchArgs.get(1)[1], equalTo((Object) 20));
                return new int[] {5, 8};
            }
            @Override
            public int[] batchUpdate(DataSource ds, String sql, List<Object[]> batchArgs) {
                String descSql = "update user_60 set name=? where id=?";
                assertThat(sql, equalTo(descSql));
                assertThat(batchArgs.size(), equalTo(1));
                assertThat(batchArgs.get(0)[0], equalTo((Object) "lily"));
                assertThat(batchArgs.get(0)[1], equalTo((Object) 60));
                return new int[] {6};
            }
        });

        List<User> users = Arrays.asList(new User(10, "ash"), new User(20, "lucy"), new User(60, "lily"));
        int[] actualInts = (int[]) operator.execute(new Object[]{users});
        assertThat(Arrays.toString(actualInts), equalTo(Arrays.toString(new int[]{5, 8, 6})));
    }

    @Test
    public void testStatsCounter() throws Exception {
        TypeToken<List<User>> pt = new TypeToken<List<User>>() {};
        TypeToken<int[]> rt = TypeToken.of(int[].class);
        String srcSql = "update user set name=:1.name where id=:1.id";
        Operator operator = getOperator(pt, rt, srcSql);

        StatsCounter sc = new StatsCounter();
        operator.setStatsCounter(sc);
        operator.setJdbcOperations(new JdbcOperationsAdapter() {
            @Override
            public int[] batchUpdate(DataSource ds, String sql, List<Object[]> batchArgs) {
                String descSql = "update user set name=? where id=?";
                assertThat(sql, equalTo(descSql));
                assertThat(batchArgs.size(), equalTo(2));
                assertThat(batchArgs.get(0)[0], equalTo((Object) "ash"));
                assertThat(batchArgs.get(0)[1], equalTo((Object) 100));
                assertThat(batchArgs.get(1)[0], equalTo((Object) "lucy"));
                assertThat(batchArgs.get(1)[1], equalTo((Object) 200));
                return new int[] {9, 7};
            }
        });
        List<User> users = Arrays.asList(new User(100, "ash"), new User(200, "lucy"));
        operator.execute(new Object[]{users});
        assertThat(sc.snapshot().getDatabaseExecuteSuccessCount(), equalTo(1L));
        operator.execute(new Object[]{users});
        assertThat(sc.snapshot().getDatabaseExecuteSuccessCount(), equalTo(2L));

        operator.setJdbcOperations(new JdbcOperationsAdapter());
        try {
            operator.execute(new Object[]{users});
        } catch (UnsupportedOperationException e) {
        }
        assertThat(sc.snapshot().getDatabaseExecuteExceptionCount(), equalTo(1L));
        try {
            operator.execute(new Object[]{users});
        } catch (UnsupportedOperationException e) {
        }
        assertThat(sc.snapshot().getDatabaseExecuteExceptionCount(), equalTo(2L));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testExecuteReturnTypeError() throws Exception {
        thrown.expect(IncorrectReturnTypeException.class);
        thrown.expectMessage("the return type of batch update expected one of " +
                "[void, int, int[], Void, Integer, Integer[]] but class java.lang.String");

        TypeToken<List<User>> pt = new TypeToken<List<User>>() {};
        TypeToken<String> rt = TypeToken.of(String.class);
        String srcSql = "update user set name=:1.name where id=:1.id";
        Operator operator = getOperator(pt, rt, srcSql);

        final int[] expectedInts = new int[] {1, 2};
        StatsCounter sc = new StatsCounter();
        operator.setStatsCounter(sc);
        operator.setJdbcOperations(new JdbcOperationsAdapter() {
            @Override
            public int[] batchUpdate(DataSource ds, String sql, List<Object[]> batchArgs) {
                String descSql = "update user set name=? where id=?";
                assertThat(sql, equalTo(descSql));
                assertThat(batchArgs.size(), equalTo(2));
                assertThat(batchArgs.get(0)[0], equalTo((Object) "ash"));
                assertThat(batchArgs.get(0)[1], equalTo((Object) 100));
                assertThat(batchArgs.get(1)[0], equalTo((Object) "lucy"));
                assertThat(batchArgs.get(1)[1], equalTo((Object) 200));
                return expectedInts;
            }
        });

        List<User> users = Arrays.asList(new User(100, "ash"), new User(200, "lucy"));
        operator.execute(new Object[]{users});
    }

    private Operator getOperator(TypeToken<?> pt, TypeToken<?> rt, String srcSql) throws Exception {
        List<Annotation> empty = Collections.emptyList();
        ParameterDescriptor p = new ParameterDescriptor(0, pt.getType(), empty, "1");
        List<ParameterDescriptor> pds = Arrays.asList(p);

        List<Annotation> methodAnnos = new ArrayList<Annotation>();
        methodAnnos.add(new MockDB());
        methodAnnos.add(new MockSQL(srcSql));
        ReturnDescriptor rd = new ReturnDescriptor(rt.getType(), methodAnnos);
        MethodDescriptor md = new MethodDescriptor(null, rd, pds);

        OperatorFactory factory = new OperatorFactory(
                new SimpleDataSourceFactory(DataSourceConfig.getDataSource()),
                null, new InterceptorChain(), null, new Config());

        Operator operator = factory.getOperator(md, new StatsCounter());
        return operator;
    }

    private Operator getOperator2(TypeToken<?> pt, TypeToken<?> rt, String srcSql) throws Exception {
        List<Annotation> pAnnos = new ArrayList<Annotation>();
        pAnnos.add(new MockShardingBy("id"));
        ParameterDescriptor p = new ParameterDescriptor(0, pt.getType(), pAnnos, "1");
        List<ParameterDescriptor> pds = Arrays.asList(p);

        List<Annotation> methodAnnos = new ArrayList<Annotation>();
        methodAnnos.add(new MockDB("", "user"));
        methodAnnos.add(new MockSharding(ModHundredTableShardingStrategy.class, MyDatabaseShardingStrategy.class, null));
        methodAnnos.add(new MockSQL(srcSql));
        ReturnDescriptor rd = new ReturnDescriptor(rt.getType(), methodAnnos);
        MethodDescriptor md = new MethodDescriptor(null, rd, pds);


        Map<String, DataSourceFactory> map = new HashMap<String, DataSourceFactory>();
        map.put("l50", new SimpleDataSourceFactory(DataSourceConfig.getDataSource(0)));
        map.put("g50", new SimpleDataSourceFactory(DataSourceConfig.getDataSource(1)));
        DataSourceFactory dsf = new MultipleDatabaseDataSourceFactory(map);
        OperatorFactory factory = new OperatorFactory(dsf, null, new InterceptorChain(), null, new Config());
        Operator operator = factory.getOperator(md, new StatsCounter());
        return operator;
    }

    public static class MyDatabaseShardingStrategy implements DatabaseShardingStrategy {

        @Override
        public String getDatabase(Object shardParam) {
            Integer i = (Integer) shardParam;
            if (i < 50) {
                return "l50";
            } else {
                return "g50";
            }
        }
    }

}
