create table if not exists public.users
(
    id               bigserial
    primary key,
    first_name       varchar,
    last_name        varchar,
    created          timestamp,
    email            varchar,
    average_progress integer
);

alter table public.users
    owner to postgres;

create table if not exists public.reviews
(
    id                  bigserial
    primary key,
    reviews_description varchar,
    created_at          timestamp,
    user_id             bigint
    constraint reviews_users_id_fk
    references public.users
);

alter table public.reviews
    owner to postgres;

create table if not exists public.products
(
    products_title       varchar,
    id                   bigserial
    primary key,
    products_description varchar,
    products_price       bigint
);

alter table public.products
    owner to postgres;

create table if not exists public.purchase_history
(
    id           bigserial
    primary key,
    product_id   bigint
    constraint purchase_history___product_fk
    references public.products,
    total_amount bigint,
    user_id      bigint
    constraint purchase_history___user_fk
    references public.users
);

alter table public.purchase_history
    owner to postgres;

create trigger purchase_history_trigger2
    after insert
    on public.purchase_history
    for each row
    execute procedure public.update_balance_on_purchase_history2();

create table if not exists public.posts
(
    id             bigserial
    primary key,
    post_text      varchar,
    description    varchar,
    post_likes     bigint,
    post_dislikes  bigint,
    comment_number bigint,
    created_date   timestamp,
    user_id        bigint
    constraint posts_users_id_fk
    references public.users
);

alter table public.posts
    owner to postgres;

create table if not exists public.notifications
(
    id                    bigserial
    primary key,
    user_id               bigint
    constraint notifications_users_id_fk
    references public.users,
    notifications_message varchar,
    created_at            timestamp
);

alter table public.notifications
    owner to postgres;

create table if not exists public.labels
(
    id              bigserial
    primary key,
    labels_category varchar,
    labels_priority varchar,
    labels_style    varchar
);

alter table public.labels
    owner to postgres;

create table if not exists public.comments
(
    id               bigserial
    primary key,
    comments_content varchar,
    user_id          bigint
    constraint comments___fk_user
    references public.users,
    post_id          bigint
    constraint comments___fk_post
    references public.posts
);

alter table public.comments
    owner to postgres;

create table if not exists public.achievements
(
    id                       bigserial
    primary key,
    achievements_name        varchar,
    achievements_description varchar,
    achievements_styles      varchar
);

alter table public.achievements
    owner to postgres;

create table if not exists public.l_achievements_users
(
    id             bigserial
    primary key,
    achievement_id bigint
    constraint l_achievements_users___fk_achievement
    references public.achievements,
    users_id       bigint
    constraint l_achievements_users___fk_user
    references public.users
);

alter table public.l_achievements_users
    owner to postgres;

create trigger achievement_trigger
    after insert
    on public.l_achievements_users
    for each row
    execute procedure public.update_balance_on_achievement();

create table if not exists public.l_products_users
(
    id          bigserial
    primary key,
    products_id bigint
    constraint l_products_users___fk_product
    references public.products,
    users_id    integer
    constraint l_products_users___fk_user
    references public.users
);

alter table public.l_products_users
    owner to postgres;

create table if not exists public.security_credentials
(
    id            bigserial
    primary key,
    user_login    varchar,
    user_password varchar,
    user_role     varchar,
    user_id       bigint
    constraint security_credentials_users_id_fk
    references public.users
);

alter table public.security_credentials
    owner to postgres;

create table if not exists public.tasks
(
    id                  bigserial
    primary key,
    tasks_title         varchar,
    tasks_description   varchar,
    tasks_creation_date timestamp,
    tasks_due_date      timestamp,
    time_for_task       bigint,
    tasks_check         boolean,
    progress            integer,
    user_id             bigint
    constraint tasks___fk_user
    references public.users,
    category_id         bigint
    constraint tasks___fk_category
    references public.labels,
    is_overdue          boolean
);

alter table public.tasks
    owner to postgres;

create trigger update_progress_trigger
    after update
        of progress
    on public.tasks
    for each row
    execute procedure public.update_average_progress();

create trigger update_tasks_check_trigger
    before update
        of progress
    on public.tasks
    for each row
    execute procedure public.update_tasks_check();

create trigger update_wallet_balance_trigger
    after update
        of tasks_check
    on public.tasks
    for each row
    execute procedure public.update_wallet_balance();

create table if not exists public.wallets
(
    id      bigserial
    primary key,
    balance bigint,
    user_id bigint
    constraint wallets_users_id_fk
    references public.users
);

alter table public.wallets
    owner to postgres;

create or replace function public.update_average_progress() returns trigger
    language plpgsql
as
$$
BEGIN
    IF NEW.progress = 100 THEN
UPDATE users
SET average_progress = (
    SELECT ROUND(AVG(progress), 0)
    FROM tasks
    WHERE tasks.user_id = NEW.user_id
)
WHERE id = NEW.user_id;
END IF;
RETURN NEW;
END;
$$;

alter function public.update_average_progress() owner to postgres;

create or replace function public.update_tasks_check() returns trigger
    language plpgsql
as
$$
BEGIN
    IF NEW.progress = 100 THEN
        NEW.tasks_check = TRUE;
END IF;
RETURN NEW;
END;
$$;

alter function public.update_tasks_check() owner to postgres;

create or replace function public.update_wallet_balance() returns trigger
    language plpgsql
as
$$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM tasks
        WHERE tasks.user_id = NEW.user_id
          AND tasks.tasks_check = TRUE
    ) THEN
UPDATE wallets
SET balance = balance + 100
WHERE wallets.user_id = NEW.user_id;
END IF;
RETURN NEW;
END;
$$;

alter function public.update_wallet_balance() owner to postgres;

create or replace procedure public.add_achievement_user(IN achievement_id_param bigint, IN user_id_param bigint)
    language plpgsql
as
$$
BEGIN
INSERT INTO l_achievements_users (achievement_id, users_id)
VALUES (achievement_id_param, user_id_param);
END;
$$;

alter procedure public.add_achievement_user(bigint, bigint) owner to postgres;

create or replace function public.update_balance_on_achievement() returns trigger
    language plpgsql
as
$$
BEGIN
    -- Проверяем, существует ли запись для пользователя в l_achievements_users
    IF EXISTS (
        SELECT 1
        FROM l_achievements_users
        WHERE users_id = NEW.users_id
    ) THEN
        -- Если запись найдена, обновляем баланс в таблице wallets
UPDATE wallets
SET balance = balance + 500
WHERE user_id = NEW.users_id;
END IF;
RETURN NEW;
END;
$$;

alter function public.update_balance_on_achievement() owner to postgres;

create or replace function public.update_balance_on_purchase_history2() returns trigger
    language plpgsql
as
$$
BEGIN
    -- Обновляем баланс в таблице wallets
UPDATE wallets
SET balance = balance - NEW.total_amount
WHERE user_id = NEW.user_id;

RETURN NEW;
END;
$$;

alter function public.update_balance_on_purchase_history2() owner to postgres;

