package edu.whu;

import edu.whu.domain.Api;
import edu.whu.domain.Params;
import edu.whu.domain.Source;
import edu.whu.service.IApiService;
import edu.whu.service.IParamsService;
import edu.whu.service.ISourceService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ToolsTest {

    @Autowired
    private IApiService apiService;

    @Autowired
    private IParamsService paramsService;

    @Autowired
    private ISourceService sourceService;

    @Test
    public void AddTest() {
        Assertions.assertNotNull(apiService);
        Api test = new Api();
        test.setName("testapi");
        test.setDescription("this is a test");
        test.setSource(1L);
        test.setMethod(1);
        Long id = apiService.addNewApi(test);
        Assertions.assertNotNull(id);

        Params params = new Params();
        params.setPos(1);
        params.setType("23");
        params.setApiId(1L);
        params.setName("testparam");
        Long pid = paramsService.addNewParams(params);
        Assertions.assertNotNull(pid);

    }

    @Test
    public void getTest() {
        List<Source> sourceList = sourceService.getAllSources();
        Assertions.assertNotNull(sourceList);
        Assertions.assertNotEquals(0, sourceList.size());
    }
}
