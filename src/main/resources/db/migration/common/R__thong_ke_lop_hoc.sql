drop view if exists thong_ke_lop_hoc cascade;

create or replace view thong_ke_lop_hoc as
select c.id                                              as id,
       c.create_at                                       as create_at,
       c.created_by                                      as created_by,
       c.update_at                                       as update_at,
       c.updated_by                                      as updated_by,
       c.limit_register                                  as limit_register,
       c.status                                          as status,
       c.day                                             as day,
       c.teacher_id                                      as teacher_id,
       c.time                                            as time,
       c.course_id                                       as course_id,
       c.place_id                                        as place_id,
       c.code                                            as code,
       c.deleted                                         as deleted,
       count(case when cr.deleted = 0 then 1 else 0 end) as so_luong
from class c
         left join class_register cr
                   on c.id = cr.class_id
group by c.id, c.create_at, c.created_by, c.update_at, c.updated_by, c.limit_register, c.status, c.day, c.teacher_id,
         c.time, c.course_id, c.place_id
;