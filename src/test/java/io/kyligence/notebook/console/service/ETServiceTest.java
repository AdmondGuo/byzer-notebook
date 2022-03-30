package io.kyligence.notebook.console.service;

import io.kyligence.notebook.console.NotebookLauncherBaseTest;
import io.kyligence.notebook.console.bean.dto.ETNodeDTO;
import io.kyligence.notebook.console.bean.entity.ETParamsDef;
import io.kyligence.notebook.console.dao.ETParamsDefRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertNotNull;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * 05/03/2022 hellozepp(lisheng.zhanglin@163.com)
 */
public class ETServiceTest extends NotebookLauncherBaseTest {
    private static final String ALSInPlaceModelParams = "[{\"param\":\"evaluateTable\",\"description\":\"The table name to evaluate the model performance in training stage\",\"value\":\"(undefined)\",\"extra\":\"{\\\"name\\\":\\\"evaluateTable\\\",\\\"value\\\":\\\"\\\",\\\"extra\\\":{\\\"doc\\\":\\\"The table name to evaluate the model performance in training stage\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"string\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"undefined\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Text\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"itemRec\",\"description\":\"\",\"value\":\"(undefined)\",\"extra\":\"{\\\"name\\\":\\\"itemRec\\\",\\\"value\\\":\\\"\\\",\\\"extra\\\":{\\\"doc\\\":\\\"\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"int\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"undefined\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Text\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"keepVersion\",\"description\":\"If set true, then every time you run the \\\" +\\n    \\\"algorithm, it will generate a new directory to save the model.\",\"value\":\"(default: true)\",\"extra\":\"{\\\"name\\\":\\\"keepVersion\\\",\\\"values\\\":[{\\\"name\\\":\\\"keepVersion\\\",\\\"value\\\":\\\"true\\\"},{\\\"name\\\":\\\"keepVersion\\\",\\\"value\\\":\\\"false\\\"}],\\\"extra\\\":{\\\"doc\\\":\\\"If set true, then every time you run the \\\\\\\" +\\\\n    \\\\\\\"algorithm, it will generate a new directory to save the model.\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"boolean\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"true\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Select\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"userRec\",\"description\":\"\",\"value\":\"(undefined)\",\"extra\":\"{\\\"name\\\":\\\"userRec\\\",\\\"value\\\":\\\"\\\",\\\"extra\\\":{\\\"doc\\\":\\\"\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"int\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"undefined\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Text\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"fitParam.[group].alpha\",\"description\":\"alpha for implicit preference\",\"value\":\"alpha: alpha for implicit preference (default: 1.0)\",\"extra\":\"{\\\"name\\\":\\\"alpha\\\",\\\"value\\\":\\\"Some(1.0)\\\",\\\"extra\\\":{\\\"doc\\\":\\\"alpha for implicit preference\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"double\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"1.0\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Text\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"fitParam.[group].blockSize\",\"description\":\"block size for stacking input data in matrices. Data is stacked within partitions. If block size is more than remaining data in a partition then it is adjusted to the size of this data.\",\"value\":\"blockSize: block size for stacking input data in matrices. Data is stacked within partitions. If block size is more than remaining data in a partition then it is adjusted to the size of this data. (default: 4096)\",\"extra\":\"{\\\"name\\\":\\\"blockSize\\\",\\\"value\\\":\\\"Some(4096)\\\",\\\"extra\\\":{\\\"doc\\\":\\\"block size for stacking input data in matrices. Data is stacked within partitions. If block size is more than remaining data in a partition then it is adjusted to the size of this data.\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"int\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"4096\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Text\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"fitParam.[group].checkpointInterval\",\"description\":\"set checkpoint interval (>= 1) or disable checkpoint (-1). E.g. 10 means that the cache will get checkpointed every 10 iterations. Note: this setting will be ignored if the checkpoint directory is not set in the SparkContext\",\"value\":\"checkpointInterval: set checkpoint interval (>= 1) or disable checkpoint (-1). E.g. 10 means that the cache will get checkpointed every 10 iterations. Note: this setting will be ignored if the checkpoint directory is not set in the SparkContext (default: 10)\",\"extra\":\"{\\\"name\\\":\\\"checkpointInterval\\\",\\\"value\\\":\\\"Some(10)\\\",\\\"extra\\\":{\\\"doc\\\":\\\"set checkpoint interval (>= 1) or disable checkpoint (-1). E.g. 10 means that the cache will get checkpointed every 10 iterations. Note: this setting will be ignored if the checkpoint directory is not set in the SparkContext\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"int\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"10\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Text\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"fitParam.[group].coldStartStrategy\",\"description\":\"strategy for dealing with unknown or new users/items at prediction time. This may be useful in cross-validation or production scenarios, for handling user/item ids the model has not seen in the training data. Supported values: nan,drop.\",\"value\":\"coldStartStrategy: strategy for dealing with unknown or new users/items at prediction time. This may be useful in cross-validation or production scenarios, for handling user/item ids the model has not seen in the training data. Supported values: nan,drop. (default: nan)\",\"extra\":\"{\\\"name\\\":\\\"coldStartStrategy\\\",\\\"value\\\":\\\"Some(nan)\\\",\\\"extra\\\":{\\\"doc\\\":\\\"strategy for dealing with unknown or new users/items at prediction time. This may be useful in cross-validation or production scenarios, for handling user/item ids the model has not seen in the training data. Supported values: nan,drop.\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"string\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"nan\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Text\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"fitParam.[group].finalStorageLevel\",\"description\":\"StorageLevel for ALS model factors.\",\"value\":\"finalStorageLevel: StorageLevel for ALS model factors. (default: MEMORY_AND_DISK)\",\"extra\":\"{\\\"name\\\":\\\"finalStorageLevel\\\",\\\"value\\\":\\\"Some(MEMORY_AND_DISK)\\\",\\\"extra\\\":{\\\"doc\\\":\\\"StorageLevel for ALS model factors.\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"string\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"MEMORY_AND_DISK\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Text\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"fitParam.[group].implicitPrefs\",\"description\":\"whether to use implicit preference\",\"value\":\"implicitPrefs: whether to use implicit preference (default: false)\",\"extra\":\"{\\\"name\\\":\\\"implicitPrefs\\\",\\\"values\\\":[{\\\"name\\\":\\\"implicitPrefs\\\",\\\"value\\\":\\\"true\\\"},{\\\"name\\\":\\\"implicitPrefs\\\",\\\"value\\\":\\\"false\\\"}],\\\"extra\\\":{\\\"doc\\\":\\\"whether to use implicit preference\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"boolean\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"false\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Select\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"fitParam.[group].intermediateStorageLevel\",\"description\":\"StorageLevel for intermediate datasets. Cannot be 'NONE'.\",\"value\":\"intermediateStorageLevel: StorageLevel for intermediate datasets. Cannot be 'NONE'. (default: MEMORY_AND_DISK)\",\"extra\":\"{\\\"name\\\":\\\"intermediateStorageLevel\\\",\\\"value\\\":\\\"Some(MEMORY_AND_DISK)\\\",\\\"extra\\\":{\\\"doc\\\":\\\"StorageLevel for intermediate datasets. Cannot be 'NONE'.\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"string\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"MEMORY_AND_DISK\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Text\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"fitParam.[group].itemCol\",\"description\":\"column name for item ids. Ids must be within the integer value range.\",\"value\":\"itemCol: column name for item ids. Ids must be within the integer value range. (default: item)\",\"extra\":\"{\\\"name\\\":\\\"itemCol\\\",\\\"value\\\":\\\"Some(item)\\\",\\\"extra\\\":{\\\"doc\\\":\\\"column name for item ids. Ids must be within the integer value range.\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"string\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"item\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Text\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"fitParam.[group].maxIter\",\"description\":\"maximum number of iterations (>= 0)\",\"value\":\"maxIter: maximum number of iterations (>= 0) (default: 10)\",\"extra\":\"{\\\"name\\\":\\\"maxIter\\\",\\\"value\\\":\\\"Some(10)\\\",\\\"extra\\\":{\\\"doc\\\":\\\"maximum number of iterations (>= 0)\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"int\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"10\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Text\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"fitParam.[group].nonnegative\",\"description\":\"whether to use nonnegative constraint for least squares\",\"value\":\"nonnegative: whether to use nonnegative constraint for least squares (default: false)\",\"extra\":\"{\\\"name\\\":\\\"nonnegative\\\",\\\"values\\\":[{\\\"name\\\":\\\"nonnegative\\\",\\\"value\\\":\\\"true\\\"},{\\\"name\\\":\\\"nonnegative\\\",\\\"value\\\":\\\"false\\\"}],\\\"extra\\\":{\\\"doc\\\":\\\"whether to use nonnegative constraint for least squares\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"boolean\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"false\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Select\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"fitParam.[group].numItemBlocks\",\"description\":\"number of item blocks\",\"value\":\"numItemBlocks: number of item blocks (default: 10)\",\"extra\":\"{\\\"name\\\":\\\"numItemBlocks\\\",\\\"value\\\":\\\"Some(10)\\\",\\\"extra\\\":{\\\"doc\\\":\\\"number of item blocks\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"int\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"10\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Text\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"fitParam.[group].numUserBlocks\",\"description\":\"number of user blocks\",\"value\":\"numUserBlocks: number of user blocks (default: 10)\",\"extra\":\"{\\\"name\\\":\\\"numUserBlocks\\\",\\\"value\\\":\\\"Some(10)\\\",\\\"extra\\\":{\\\"doc\\\":\\\"number of user blocks\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"int\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"10\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Text\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"fitParam.[group].predictionCol\",\"description\":\"prediction column name\",\"value\":\"predictionCol: prediction column name (default: prediction)\",\"extra\":\"{\\\"name\\\":\\\"predictionCol\\\",\\\"value\\\":\\\"Some(prediction)\\\",\\\"extra\\\":{\\\"doc\\\":\\\"prediction column name\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"string\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"prediction\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Text\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"fitParam.[group].rank\",\"description\":\"rank of the factorization\",\"value\":\"rank: rank of the factorization (default: 10)\",\"extra\":\"{\\\"name\\\":\\\"rank\\\",\\\"value\\\":\\\"Some(10)\\\",\\\"extra\\\":{\\\"doc\\\":\\\"rank of the factorization\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"int\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"10\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Text\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"fitParam.[group].ratingCol\",\"description\":\"column name for ratings\",\"value\":\"ratingCol: column name for ratings (default: rating)\",\"extra\":\"{\\\"name\\\":\\\"ratingCol\\\",\\\"value\\\":\\\"Some(rating)\\\",\\\"extra\\\":{\\\"doc\\\":\\\"column name for ratings\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"string\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"rating\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Text\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"fitParam.[group].regParam\",\"description\":\"regularization parameter (>= 0)\",\"value\":\"regParam: regularization parameter (>= 0) (default: 0.1)\",\"extra\":\"{\\\"name\\\":\\\"regParam\\\",\\\"value\\\":\\\"Some(0.1)\\\",\\\"extra\\\":{\\\"doc\\\":\\\"regularization parameter (>= 0)\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"double\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"0.1\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Text\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"fitParam.[group].seed\",\"description\":\"random seed\",\"value\":\"seed: random seed (default: 1994790107)\",\"extra\":\"{\\\"name\\\":\\\"seed\\\",\\\"value\\\":\\\"Some(1994790107)\\\",\\\"extra\\\":{\\\"doc\\\":\\\"random seed\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"long\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"1994790107\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Text\\\",\\\"valueProvider\\\":{}}\"},{\"param\":\"fitParam.[group].userCol\",\"description\":\"column name for user ids. Ids must be within the integer value range.\",\"value\":\"userCol: column name for user ids. Ids must be within the integer value range. (default: user)\",\"extra\":\"{\\\"name\\\":\\\"userCol\\\",\\\"value\\\":\\\"Some(user)\\\",\\\"extra\\\":{\\\"doc\\\":\\\"column name for user ids. Ids must be within the integer value range.\\\",\\\"label\\\":\\\"\\\",\\\"options\\\":{\\\"valueType\\\":\\\"string\\\",\\\"derivedType\\\":\\\"NONE\\\",\\\"required\\\":\\\"false\\\",\\\"defaultValue\\\":\\\"user\\\",\\\"currentValue\\\":\\\"undefined\\\"}},\\\"tpe\\\":\\\"Text\\\",\\\"valueProvider\\\":{}}\"}]";

    @Autowired
    private ETService etService;

    @Test
    public void testGetAllET() {
        int size = etService.getAllET().size();
        assert size > 0;
        System.out.println("[testGetAllET] getAllET:" + size);
    }

    @Resource
    private ETParamsDefRepository etParamsDefRepository;

    @Test
    public void deleteById() {
        List<ETParamsDef> rows = etParamsDefRepository.findAllByEtId(1);
        System.out.println(rows.toString());
    }

    @Test
    public void testGetETKeyParams() {
        // load modelParams.`ALSInPlace` as output;
        clean("/run/script", "POST", client);
        client.reset();
        client.when(request().withPath("/run/script").withMethod("POST")
        ).respond(response().withBody(ALSInPlaceModelParams));
        HashMap<Integer, ETNodeDTO> etCache = ETService.etCache;
        Map.Entry<Integer, ETNodeDTO> entry = etCache.entrySet().iterator().next();
        Integer etId = entry.getKey();
        ETNodeDTO etNode = entry.getValue();
        Map<String, List<String>> etKeyParams = etService.getETKeyParams(etId);
        assert etKeyParams.size() > 0;
        System.out.println("[testGetETKeyParams] testGetETKeyParams:" + etKeyParams);

        String etName = etService.getETName(etId);
        assertNotNull(etName);

        List<ETParamsDef> etParams = etService.getEtParams(etNode);
        assert etParams.size() > 0;
    }

}
