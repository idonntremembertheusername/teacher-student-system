package com.ts.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ts.entity.BaseResp;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * @author thesky
 */
@RequestMapping("/File")
@RestController
public class FileController {

    private final String projectPath = System.getProperty("user.dir");

    private final String fileDirPath = projectPath.substring(0, projectPath.lastIndexOf(File.separator) + 1) + "upload" + File.separator;

    @ResponseBody
    @PostMapping
    public BaseResp importData(@RequestParam(value = "file") MultipartFile file, HttpServletRequest req) throws IOException {

        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            return BaseResp.fail("01","上传的文件有误");
        }

        int userId = Integer.parseInt(req.getParameter("userId"));

        String filePath = fileDirPath + userId+ File.separator + fileName;
        File newFile = new File(filePath);
        if (!newFile.exists()) {
            Files.createDirectories(newFile.toPath());
        }
        file.transferTo(newFile);
        return BaseResp.success(fileName);
    }

    @GetMapping
    public void getFile(@RequestParam Integer userId,@RequestParam String name, HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();

//        int userId = Integer.parseInt(request.getParameter("userId"));
//        String name = request.getParameter("name");
        String filePath = fileDirPath + userId + File.separator + name;
        response.setHeader("Content-Type", "application/octet-stream;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(name, "UTF-8"));
        byte[] bytes = new byte[1024];
        try (FileInputStream fileInputStream = new FileInputStream(new File(filePath))) {
            int i = 0;
            while (i != -1) {
                i = fileInputStream.read(bytes);
                outputStream.write(bytes);
            }
        }
        outputStream.close();
    }


    @DeleteMapping
    public BaseResp deleteFile(@RequestParam Integer userId,@RequestParam String name, HttpServletResponse response) throws IOException {
//        int userId = Integer.parseInt(request.getParameter("userId"));
//        String name = request.getParameter("name");
        String filePath = fileDirPath + userId  + File.separator + name;
        //这里因为我文件是相对路径 所以需要在路径前面加一个点
        File file = new File(filePath);
        if (file.exists()){//文件是否存在
            file.delete();//删除文件
        }
        return BaseResp.success("删除文件成功");
    }
}
