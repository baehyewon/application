var express = require('express');
var mysql = require('mysql');
var router = express.Router();
var connection = mysql.createConnection({   //mysql 연결 rds 주소
  'host' : 'aws-rds.cfsyy1ya3o9o.ap-northeast-2.rds.amazonaws.com',
  'user' : 'user',
  'password' : '2014111614',
  'database' : 'share_park',
});

/*
 * Method       : POST
 * Path         : http://localhost:3000/login
 * Description  : 로그인을 시도합니다.
 */

router.post('/changePassword', function(req, res, next) { //post(보안)는 보내기 get은 요청 둘 다 정보는 보냄
    var email= req.body.email;
    var password= req.body.password;
    var newPassword= req.body.newPassword;


    connection.query('select * from user where email=? and password;',
        [email, password],
        function(error, cursor){
            if(!error){
                if(!(cursor.length==0))
                {
                    //debug
                    connection.query('update user set password=? where email=?;',
                             [email, password],
                             function (error, cur) {
                              if(!error){
                                console.log("비밀번호 수정 성공");
                                res.status(200).json({result: true});
                              }
                              else{
                                console.log("비밀번호 수정 실패!!\n" + error);
                                res.status(404).json({result: false});
                              }
                            });
                } else {
                    console.log("이메일과 비밀번호가 일치하지 않습니다");
                    res.status(405).json({result: false});
                }
            } else {
                console.log("DB Error!!!!\n" + error);
                res.status(500).json({result: false});
            }
        });
});

//미완성

router.post('/reset_pass', function (req, res, next) { 
  var post_pk = req.body.post_pk;
  var post_user = req.body.post_user;
   
  connection.query('select * from post where post_pk=? and post_user=?;',
    [post_pk, post_user],
    function (error, cursor) {
      if (!error) {
        if (!(cursor.length == 0)) { //id 주인이 포스트를 만든것이 맞는 상황 
            connection.query('delete from post where post_pk=?;',
                             [post_pk],
                             function (error, cur) {
                              if(!error){
                                console.log("post삭제 성공");
                                res.status(200).json({result: true});
                              }
                              else{
                                console.log("post삭제 실패!!\n" + error);
                                res.status(404).json({result: false});
                              }
                            });
          } else {
          console.log("사용자가 만든 포스트가 아니기 때문에 삭제할 수 없습니다.");
          res.status(405).json({result: false});
            }
      } else {
        console.log("DB Error!!!!\n" + error);
        res.status(500).json({result: false});
        }
    });
});

module.exports = router;