alter table CLINIC_VISIT add column HOURS_SPENT integer ^
update CLINIC_VISIT set HOURS_SPENT = 0 where HOURS_SPENT is null ;
alter table CLINIC_VISIT alter column HOURS_SPENT set not null ;
