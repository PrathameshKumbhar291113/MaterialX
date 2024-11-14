package com.prathameshkumbhar.materialx

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.databinding.DataBindingUtil
import com.prathameshkumbhar.materialx.databinding.ActivityJpcBinding
import com.prathameshkumbhar.materialx.motion_sync.MaterialXMotionSync
import com.prathameshkumbhar.materialx.motion_sync.utils.MotionSyncAlignment
import com.prathameshkumbhar.materialx.motion_sync.utils.MotionSyncLayout
import com.prathameshkumbhar.materialx.ui.theme.MaterialXJPCTheme


@SuppressLint("RestrictedApi")
class JPCActivity() : ComponentActivity() {
    private lateinit var binding: ActivityJpcBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@JPCActivity, R.layout.activity_jpc)

        binding.composeView.setContent {
            MaterialXJPCTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onBackground
                ) {
                    MaterialXMotionSyncExample()
                }
            }
        }
    }
}


@Composable
fun MaterialXMotionSyncExample() {

    val shapesList: List<@Composable () -> Unit> = listOf(
        {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(colorResource(id = R.color.heliotrope1))
            )
        },
        {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(colorResource(id = R.color.heliotrope2))
            )
        },
        {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(colorResource(id = R.color.heliotrope1))
            )
        },
    )

    MaterialXMotionSync(
        composableList = shapesList,
        size = 80.dp,
        layoutDirection = MotionSyncLayout.COLUMN,
        alignmentOption = MotionSyncAlignment.CENTER,
        spacing = 20.dp,
        speedMultiplier = 1f
    )

}