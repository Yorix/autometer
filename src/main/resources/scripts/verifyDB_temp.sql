USE carcalculator;

UPDATE `note`
SET `value` = `value` * -1
WHERE `id` < 10000 AND `value` > 0;