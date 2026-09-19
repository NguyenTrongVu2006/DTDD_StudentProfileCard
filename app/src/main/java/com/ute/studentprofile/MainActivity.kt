package com.ute.studentprofile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ute.studentprofile.databinding.ActivityMainBinding
import com.ute.studentprofile.model.Student
import com.ute.studentprofile.utils.showConfirmDialog
import com.ute.studentprofile.utils.toAcademicRanking
import com.ute.studentprofile.utils.toast
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    // Thông tin sinh viên
    private var currentStudent: Student? = Student(
        id = "2415053122150",
        name = "Nguyễn Trọng Vũ",
        className = "24T1",
        email = "2415053122150@sv.ute.udn.vn",
        phoneNumber = "0343098279",
        gpa = 3.6
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentStudent?.let { bindStudentData(it) }
        // 1. Cập nhật GPA
        binding.btnUpdateGpa.setOnClickListener {
            val inputStr = binding.edtNewGpa.text.toString().trim()
            val newGpa = inputStr.toDoubleOrNull()

            if (newGpa == null || newGpa !in 0.0..4.0) {
                binding.edtNewGpa.error = "Vui lòng nhập GPA hợp lệ (0.0 - 4.0)"
                toast("Điểm GPA không hợp lệ!")
                return@setOnClickListener
            }

            currentStudent = currentStudent?.copy(gpa = newGpa)
            currentStudent?.let { bindStudentData(it) }
            toast("Cập nhật điểm thành công!")
        }
        // 2. Bài tập 1: Mở ứng dụng gọi điện thoại bằng Implicit Intent
        binding.btnCall.setOnClickListener {
            currentStudent?.let { student ->
                val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${student.phoneNumber}")
                }
                startActivity(dialIntent)
            } ?: toast("Không có thông tin sinh viên để gọi!")
        }
        // 3. Bài tập 2: Xóa hồ sơ với AlertDialog xác nhận
        binding.btnDelete.setOnClickListener {
            showConfirmDialog(
                title = "Xác nhận xóa",
                message = "Bạn có chắc chắn muốn xóa hồ sơ sinh viên này không?"
            ) {
                // Khối callback thực thi khi bấm "Đồng ý"
                currentStudent = null
                binding.cardProfile.visibility = View.GONE
                toast("Đã xóa hồ sơ sinh viên thành công!")
            }
        }
    }
    private fun bindStudentData(student: Student) {
        with(binding) {
            tvName.text = student.name
            tvStudentId.text = "MSSV: ${student.id} | Lớp: ${student.className}"
            tvPhoneNumber.text = "SĐT: ${student.phoneNumber}"
            tvGpaBadge.text = "${student.gpa} GPA (${student.gpa.toAcademicRanking()})"
            edtNewGpa.setText(student.gpa.toString())
        }
    }
}