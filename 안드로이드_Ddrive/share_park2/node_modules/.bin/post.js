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
 * Method       : GET
 * Path         : http://52.78.20.203:3000/cate/add
 * Description  : 카테고리를 추가합니다.
 */

 //post 생성c
router.post('/create', function (req, res, next) { //post(보안)는 보내기 get은 요청 둘 다 정보는 보냄
  var email = req.body.email;
  var post_address = req.body.post_address;
  var post_start = req.body.post_start;
  var post_end = req.body.post_end;

  connection.query('insert into post (email, post_address, post_start, post_end) values (?, ?, ?, ?);',
    [email, post_address, post_start, post_end],
    function(error, cursor){
      if(!error){
        console.log("post추가 성공");
        res.status(200).json({result: true});
        //
      }else {
        console.log("DB Error!!!!\n" + error);
        res.status(500).json({result: false});
        }
    });
  });

//post삭제
router.post('/delete', function (req, res, next) { 
  var post_pk = req.body.post_pk;
  var email = req.body.email;
   
  connection.query('select * from post where post_pk=? and email=?;',
    [post_pk, email],
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