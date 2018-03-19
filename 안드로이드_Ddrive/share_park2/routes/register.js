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
 * Path         : http://localhost:3000/register
 * Description  : 회원가입을 합니다.
 */
router.post('/', function (req, res, next) { //post(보안)는 보내기 get은 요청 둘 다 정보는 보냄
  var password = req.body.password;
  var name = req.body.name;
  var email = req.body.email;
  
  

  connection.query('select * from user where email=?;',
    [email],
    function (error, cursor) {
      if (!error) {
        if (!(cursor.length == 0) && cursor[0].email == email) {
            // debug
             console.log("이미 존재하는 ID!!!!!");
          res.status(406).json({result: false});
        } else {
          // DB 삽입
          connection.query('insert into user (email, password, name) values (?, ?, ?);',
            [email, password, name],
            function (error, info) {
              if (!error) {
                  // debug
                  console.log("DB에 사용자 등록 성공!");
                res.status(200).json({result: true});
              } else {
                  // debug
                  console.log("클라이언트오류\n" + error);
                res.status(404).json({result: false});
              }
                });
            }
        } else {
        console.log("DB Error!!!!\n" + error);
        res.status(500).json({result: false});
        }
    });
});

module.exports = router;