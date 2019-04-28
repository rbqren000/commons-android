package cn.zfs.commonsdemo

import android.content.Intent
import android.os.Bundle
import com.snail.commons.utils.isDebugApk
import com.snail.commons.utils.isRunInDebug
import com.snail.fileselector.FileSelector
import com.snail.fileselector.OnFileSelectListener
import kotlinx.android.synthetic.main.activity_debug_judge.*
import java.io.File
import java.io.FilenameFilter

/**
 * 判断是否是debug包
 *
 * date: 2019/4/27 10:53
 * author: zengfansheng
 */
class DebugJudgeActivity : BaseActivity() {
    private var fileSelector = FileSelector()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug_judge)
        fileSelector.setFilenameFilter(FilenameFilter { dir, name ->
            File(dir, name).isDirectory || name.endsWith(".apk", true)
        })
        fileSelector.setMultiSelectionEnabled(false)
        fileSelector.setSelectionMode(FileSelector.FILES_ONLY)        
        fileSelector.setOnFileSelectListener(object : OnFileSelectListener {
            override fun onFileSelect(paths: List<String>) {
                tvApkState.text = if (isDebugApk(paths[0])) "选择的apk是debug包" else "选择的apk是release包"
            }
        })
        tvAppState.text = if (isRunInDebug()) "当前app是debug包" else "当前app是release包"
        btnSelect.setOnClickListener { fileSelector.select(this) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fileSelector.onActivityResult(requestCode, resultCode, data)
    }
}