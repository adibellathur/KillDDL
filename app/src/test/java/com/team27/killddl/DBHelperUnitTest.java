package com.team27.killddl;


import android.os.Build;

import com.team27.killddl.data.DBHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP, manifest = "app/manifests/AndroidManifest.xml", constants = BuildConfig.class)
public class DBHelperUnitTest {

    private class TestRunner {

        private DBHelper helper;

        private TestRunner() {
            helper = new DBHelper(RuntimeEnvironment.application);
        } 

    }

    @Test
    public void InitialTest() {
        new TestRunner();
    }
}
